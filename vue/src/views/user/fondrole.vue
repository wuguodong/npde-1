<template>
  <div>
    <el-table :data="fondList"
              v-loading.body="listLoading"
              element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" prop="id" label="全宗号" width="60">
      </el-table-column>
      <el-table-column align="center" prop="fondName" label="全宗名" width="150"></el-table-column>
      <el-table-column align="center" label="操作权限" v-if="hasPerm('fond:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showFondRoleUpdate(scope.$index)">数据权限</el-button>
          <el-button type="danger" icon="delete" @click="removeAllFondPermission(scope.$index)">移除所有权限</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--全宗数据权限弹出对话框-->
    <el-dialog :title="textMap['update']" :visible.sync="dialogFondFormVisible" append-to-body @open="dialogOpen">
      <el-form class="small-space" label-position="left" label-width="100px"
               style='width: 600px; margin-left:50px;'>
        <el-tabs type="border-card" style="margin-right: 40px">
          <el-tab-pane label="文件管理权限">
            <div>
              <div v-for=" (menu,_index) in this.alldatapermission" :key="menu.menuName">
                <span style="display: inline-block;">
                  <el-button
                    :type="isDataPermissionMenuNone(_index)?'':(isDataPermissionAll(_index)?'success':'primary')"
                    size="large"
                    @click="checkDataAll(_index)">{{menu.menuName}}
                  </el-button>
                </span>
                <div style="display: inline-block;margin-left:20px;">
                  <el-checkbox-group v-model="fondRole.fondItemsPermissionList">
                    <el-checkbox v-for="perm in menu.permissions"
                                 :label="perm.id"
                                 :key="perm.id"
                                 @change="checkTTRequired(perm,_index)">
                      <span :class="{requiredPerm:perm.requiredPerm===1}">{{perm.permissionName}}</span>
                    </el-checkbox>
                  </el-checkbox-group>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="整理编目权限">
          </el-tab-pane>
          <el-tab-pane label="储藏室权限">

          </el-tab-pane>
          <el-tab-pane label="库房权限">

          </el-tab-pane>
        </el-tabs>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFondFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateFondDataRole">修 改</el-button>
      </div>
    </el-dialog>

  </div>


</template>
<script>
  export default {
    name: 'fondrole',
    props: {
      'alldatapermission': Array,
      'tempRole': Object
    },
    data() {
      return {
        fondRole: {
          roleName: '',
          roleId: '',
          fondItemsPermissionList: [],//条目操作权限
        },
        fondList: [],//全宗列表
        listLoading: false,//数据加载等待动画
        dialogFondFormVisible: false,
        textMap: {
          update: '数据权限编辑'
        },
        fondPermission: {
          id: "",
          roleId: "",
          permissionId: "",
          fondId: "",
          createTime: "",
          updateTime: "",
          deleteStatus: "",
          permissionName: ""
        }
      }
    },

    created() {
      this.getFondList();
    },

    methods: {
      getFondList() {
        //全宗查询列表
        this.listLoading = true;
        this.api({
          url: "/fond/listFond",
          method: "get"
        }).then(data => {
          this.listLoading = false;
          this.fondList = data.list;
        })
      },
      dialogOpen(){
      },

      getFondPermissonList() {
        this.fondRole.fondItemsPermissionList = [];
        //查询列表
        this.listLoading = true;
        if (this.fondPermission.fondId !== null && this.fondPermission.fondId !== '') {
          this.api({
            url: "/fond/listFondPermission",
            method: "post",
            data: this.fondPermission
          }).then(data => {
            this.fondPermissionList = data;
            this.listLoading = false;
            data.forEach(function (value) {
              if (value.deleteStatus !== '1') {
                this.fondRole.fondItemsPermissionList.push(Number(value.permissionId));
              }
            }, this);
          })
        }
      },


      showFondRoleUpdate($index) {
        this.fondPermission.fondId = this.fondList[$index].id;
        this.fondPermission.roleId = this.tempRole.roleId;
        this.dialogFondStatus = "update";
        this.dialogFondFormVisible = true;
        this.getFondPermissonList();
      },


      updateFondDataRole() {
        if (!this.checkFondPermissionNum()) {
          return;
        }

        let postData = [];
        debugger;
        for (let m = 0; m < this.alldatapermission.length; m++) {
          let dataMenuPemission = this.alldatapermission[m].permissions;
          for (let n = 0; n < dataMenuPemission.length; n++) {
            let tempFondPermission = JSON.parse(JSON.stringify(this.fondPermission));
            if (dataMenuPemission[n].id != null && dataMenuPemission[n].id !== '') {
              tempFondPermission.permissionId = dataMenuPemission[n].id;
              tempFondPermission.permissionName = dataMenuPemission[n].permissionName;
              tempFondPermission.deleteStatus = 1;
            }
            postData.push(tempFondPermission);
          }
        }

        for (let n = 0; n < postData.length; n++) {
          //更新的时候某个权限还在
          if (this.fondRole.fondItemsPermissionList.indexOf(postData[n].permissionId) > -1) {
            postData[n].deleteStatus = 0;
          }
        }


//        修改角色
        this.api({
          url: "/fond/updateFondDataRole",
          method: "post",
          data: postData
        }).then(() => {
          this.getFondList();
          this.dialogFondFormVisible = false;
          this.$confirm('修改权限成功', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          })
        })
      },

      checkFondPermissionNum() {
        //校验至少有一种权限
        if (this.tempRole.permissions.length === 0) {
          this.$message.error("请至少选择一种权限");
          return false;
        }
        return true;
      },

      isDataPermissionMenuNone(_index) {
        if (this.dialogFondFormVisible == true) {
          //判断本级菜单内的权限是否一个都没选
          let permissions = this.alldatapermission[_index].permissions;
          let result = true;
          for (let j = 0; j < permissions.length; j++) {
            if (this.fondRole.fondItemsPermissionList.indexOf(permissions[j].id) > -1) {
              result = false;
              break;
            }
          }
          return result;
        }
      },

      isDataPermissionAll(_index){
        //判断本级菜单内的权限是否全选了
        let dataPermissions = this.alldatapermission[_index].permissions;
        let result = true;
        for (let j = 0; j < dataPermissions.length; j++) {
          if (this.fondRole.fondItemsPermissionList.indexOf(dataPermissions[j].id) < 0) {
            result = false;
            break;
          }
        }
        return result;
      },

      checkDataPermissionAll(_index){
        //点击菜单   相当于全选按钮
        let v = this;
        if (v.isDataPermissionAll(_index)) {
          //如果已经全选了,则全部取消
          v.noPerm(_index);
        } else {
          //如果尚未全选,则全选
          v.allPerm(_index);
        }
      },

      checkDataAll(_index) {
        //点击菜单   相当于全选按钮
        let v = this;
        if (v.isDataPermissionAll(_index)) {
          //如果已经全选了,则全部取消
          v.noDataPerm(_index);
        } else {
          //如果尚未全选,则全选
          v.allDataPerm(_index);
        }
      },

      allDataPerm(_index) {
        //全部选中
        let permissions = this.alldatapermission[_index].permissions;
        for (let j = 0; j < permissions.length; j++) {
          this.addUnique(permissions[j].id, this.fondRole.fondItemsPermissionList)
        }
      },

      noDataPerm(_index) {
        let permissions = this.alldatapermission[_index].permissions;
        for (let j = 0; j < permissions.length; j++) {
          let idIndex = this.fondRole.fondItemsPermissionList.indexOf(permissions[j].id);
          if (idIndex > -1) {
            this.fondRole.fondItemsPermissionList.splice(idIndex, 1);
          }
        }
      },

      addUnique(val, arr) {
        //数组内防重复地添加元素
        let _index = arr.indexOf(val);
        if (_index < 0) {
          arr.push(val);
        }
      },

      checkTTRequired(_perm, _index) {
        //本方法会在勾选状态改变之后触发
        let permId = _perm.id;
        if (this.tempRole.permissions.indexOf(permId) > -1) {
          //选中事件
          //如果之前未勾选本权限,现在勾选完之后,tempRole里就会包含本id
          //那么就要将必选的权限勾上
          this.makeReuqiredPermissionChecked(_index);
        } else {
          //取消选中事件
          if (_perm.requiredPerm === 1) {
            //如果是必勾权限,就把本菜单的权限全部移出
            //(其实也可以提示用户本权限是菜单里的必选,请先取消勾选另外几个权限,交互太麻烦,此处就直接全部取消选中了)
            this.noPerm(_index);
          }
        }
      },

      makeReuqiredPermissionChecked(_index) {
        //将本菜单必选的权限勾上
        let dataPermissions = this.alldatapermission[_index].permissions;
        for (let j = 0; j < dataPermissions.length; j++) {
          let perm = dataPermissions[j];
          if (perm.requiredPerm === 1) {
            //找到本菜单的必选权限,将其勾上
            this.addUnique(perm.id, this.tempRole.permissions)
          }
        }
      },

      //删除某个角色对某个全宗的所有权限
      removeAllFondPermission(_index){

      }
    }
  }
</script>
