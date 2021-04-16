var vm = new Vue({
    el:'#userdadiv',
    data:{
        userlist:[],
        entity:{},
        dlist:[],
        deptids:[],
        deptid:0
    },
    methods:{
        getUserList:function () {
            var _this=this;
            axios.get("../user/getUserList.do").then(function (response) {
                _this.userlist=response.data;
            })
        },
        toUserDept:function (id) {
            var _this=this;
            //查处这个用户已经拥有的部门id，和全部部门
            axios.get("../user/getUserVoById.do?id="+id).then(function (response) {
                _this.entity=response.data;
                _this.dlist=response.data.dlist;
                _this.deptids=response.data.deptids;
                document.getElementById("userdeptdiv").style.display="block";
            })
        },
        saveUserDept:function () {
            var _this =this;
            axios.post("../user/saveUserDept.do?id="+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    document.getElementById("userdeptdiv").style.display="none";
                    _this.getUserList();
                }else{
                    alert(response.msg);
                }
            })
        }
    }
});
vm.getUserList();