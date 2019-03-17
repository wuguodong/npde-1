<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('organization:add')">添加组织机构
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="code" label="机构编码" width="80">
      </el-table-column>
      <el-table-column align="center" prop="name" label="机构名称" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="principal" label="负责人" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="300"
                       v-if="hasPerm('organization:update') || hasPerm('organization:delete')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('organization:update')" @click="showUpdate(scope.$index)">
            修改
          </el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('organization:delete')"
                     @click="removeOrganization(scope.$index)">删除
          </el-button>
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
      <el-form ref="tempOrganization" :model="tempOrganization" label-width="100px">
        <el-form-item label="机构编码">
          <el-input v-model="tempOrganization.code"></el-input>
        </el-form-item>
        <el-form-item label="机构名称">
          <el-input v-model="tempOrganization.name"></el-input>
        </el-form-item>
        <el-form-item label="机构负责人">
          <el-input v-model="tempOrganization.principal"></el-input>
        </el-form-item>
      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleControl">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createOrganization">创 建</el-button>
        <el-button type="primary" v-else @click="updateOrganization">修 改</el-button>
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
          update: '编辑组织机构',
          create: '添加组织机构'
        },
        tempOrganization: {
          id: "",
          code: "",
          name: "",
          principal: ""
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
        if (!this.hasPerm('organization:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/organization/listOrganization",
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
        this.tempOrganization.name = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempOrganization = this.list[$index];
        this.tempOrganization.oldId = this.tempOrganization.id;
        this.dialogStatus = "update";
        this.dialogFormVisible = true;
      },
      createOrganization() {
        //保存新全宗
        this.api({
          url: "/organization/addOrganization",
          method: "post",
          data: this.tempOrganization
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false;
        })
      },
      updateOrganization() {
        //修改全宗
        this.api({
          url: "/organization/updateOrganization",
          method: "post",
          data: this.tempOrganization
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false;
        })
      },
      removeOrganization($index) {
        let _vue = this;
        this.$confirm('确定删除此机构?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let organization = _vue.list[$index];
          organization.deleteStatus = '2';
          _vue.api({
            url: "/organization/deleteOrganization",
            method: "post",
            data: organization
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      dialogFormVisibleControl(){
        this.getList();
        this.dialogFormVisible = false
      }

    }
  }
</script>
