<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <template>
            <a>档案库类别</a>
            <el-select v-model="archivesTypesDefaultValue" placeholder="全部">
              <el-option
                v-for="item in archivesTypesOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
          <el-button type="primary" style="float: right" icon="el-icon-circle-plus" @click="showCreate"
                     v-if="hasPerm('archiveRoom:add')">添加档案库类别
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" @row-click="tableSelect"
              element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="content" label="名称" style="width: 60px;">
        <template slot-scope="scope">
          <a style="color: darkblue">
            <router-link v-on:click.native="getList(scope.row.id)" to="">{{scope.row.content}}</router-link>
          </a>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="content" label="创建人" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200"
                       v-if="hasPerm('archiveRoom:update') || hasPerm('archiveRoom:delete')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="hasPerm('archiveRoom:update')" @click="showUpdate(scope.$index)">
            修改
          </el-button>
          <el-button type="danger" icon="delete" v-if="hasPerm('archiveRoom:delete')"
                     @click="removeArchive(scope.$index)">删除
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
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="名称">
          <el-input type="text" v-model="tempArticle.content">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createArticle">添 加</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
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
          name: '',
          parentId: ""
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '添加档案库类别'
        },
        tempArticle: {
          id: "",
          content: "",
          parentId: ""
        },
        archivesTypesOptions: [{
          label: "全部",
          value: "00"
        }, {
          label: "文书档案库",
          value: "01"
        }],
        archivesTypesDefaultValue: '全部'
      }
    },
    created() {
      this.getList();
    },
    methods: {
      //_parentId  为档案库目录的父档案库ID
      getList(_parentId) {
        debugger;
        //查询列表
        if (!this.hasPerm('archiveRoom:list')) {
          return
        }
        this.listLoading = true;
        this.listQuery.parentId = _parentId;
        this.api({
          url: "/archiveRoom/listArchiveRoom",
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
        this.tempArticle.content = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempArticle.id = this.list[$index].id;
        this.tempArticle.content = this.list[$index].content;
        this.dialogStatus = "update";
        this.dialogFormVisible = true
      },
      tableSelect($row){
        debugger;
        this.tempArticle.parentId = $row.id;
      },
      createArticle() {
        //保存档案库
        this.api({
          url: "/archiveRoom/addArchiveRoom",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateArticle() {
        //修改文章
        this.api({
          url: "/archiveRoom/updateArchiveRoom",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removeArchive($index) {
        let _vue = this;
        this.$confirm('确定删除此档案库?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let archiveRoom = _vue.list[$index];
          archiveRoom.entityStatus = "delete";
          _vue.api({
            url: "/archiveRoom/deleteArchiveRoom",
            method: "post",
            data: archiveRoom
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
<style scoped>
  .el-table__body tr.current-row > td {
    background: #4e8ff5 !important;
  }
</style>
