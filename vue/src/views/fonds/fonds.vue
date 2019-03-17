<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('fond:add')">添加全宗
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="ID" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="id" label="全宗号" width="80">
      </el-table-column>
      <el-table-column align="center" prop="fondName" label="全宗名" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="parentName" label="上级全宗" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="isStorage" label="是否立档单位" :formatter="formatterNum"
                       style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="fondDesc" label="备注" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('fond:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" @click="removeFond(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-pageNum="listQuery.pageNum"
      :pageNum-size="listQuery.pageRow"
      :total="totalCount"
      :pageNum-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>


    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="tempFond" :model="tempFond" label-width="100px">
        <el-form-item label="全宗号">
          <el-input v-model="tempFond.id"></el-input>
        </el-form-item>
        <el-form-item label="全宗名">
          <el-input v-model="tempFond.fondName"></el-input>
        </el-form-item>
        <el-form-item label="上级全宗" v-model="list">
          <el-select v-model="tempFond.parentName" placeholder="请选择上级全宗">
            <el-option
              v-for="item in list" v-if="item.id!==tempFond.id"
              :key="item.id"
              :label="item.fondName"
              :value="item.fondName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否有馆藏">
          <el-switch v-model="tempFond.isStorage" active-value="1" inactiveValue="0"></el-switch>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="tempFond.fondDesc"></el-input>
        </el-form-item>
      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createFond">创 建</el-button>
        <el-button type="primary" v-else @click="updateFond">修 改</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '添加全宗'
        },
        tempFond: {
          id: "",
          fondName: "",
          parentName: "",
          isStorage: "",
          desc: ""
        }
      }
    },
    created() {
      this.getList();
    },
    methods: {
      formatterNum(row, column, cellValue){
        if (cellValue == '0') {
          return '否'
        } else if (cellValue == '1') {
          return '是'
        }
      },
      getList() {
        //查询列表
        if (!this.hasPerm('fond:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/fond/listFond",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val;
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val;
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempFond.name = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempFond = this.list[$index];
        this.dialogStatus = "update";
        this.dialogFormVisible = true;
      },
      createFond() {
        //保存新全宗
        this.api({
          url: "/fond/addFond",
          method: "post",
          data: this.tempFond
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false;
        })
      },
      updateFond() {
        //修改全宗
        this.api({
          url: "/fond/updateFond",
          method: "post",
          data: this.tempFond
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false;
        })
      },
      removeFond($index) {
        let _vue = this;
        this.$confirm('确定删除此全宗?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let fond = _vue.list[$index];
          fond.deleteStatus = '2';
          _vue.api({
            url: "/fond/updateFond",
            method: "post",
            data: fond
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      }
    }
  }
</script>
