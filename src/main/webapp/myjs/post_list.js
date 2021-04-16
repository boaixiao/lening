var vm1 = new Vue({
   el:'#postdadiv',
   data:{
       searchEntity:{},
       postlist:[],
       page:{},
       pageNum:1,
       pageSize:3,
       meunlist:[]
   },
    methods:{
        getPostListConn:function () {
            var _this=this;
            axios.post("../post/getPostListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.pageNum = response.data.currentPage;
                _this.postlist = response.data.list;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            })
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getPostListConn();
        },
        toPostMeun:function (zid) {
            var _this=this;
            axios.get("../post/getMeunListById.do?zid="+zid).then(function (response) {
                    _this.meunlist=response.data;
                    testaa(response.data,zid);
                document.getElementById("nodes").style.display="block";
            })
        },
        savePostMeun:function (postid,ids) {
            var _this=this;
            axios.post("../post/savePostMeun.do?postid="+postid,ids).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("nodes").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            })
        }
    }
});
vm1.getPostListConn();