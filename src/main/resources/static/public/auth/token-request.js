//document.addEventListener("DOMContentLoaded", function() {
//  if (window.location.href.startsWith('http://localhost:8080/mike/admin') || window.location.href.startsWith('http://localhost:8080/mike/profile')) {
//    var accessToken = localStorage.getItem('accessToken');
////    var currentUrl=window.location.href;
//    if (!accessToken) {
//      window.location.replace('/mike/auth/login');
//    } else {
//      $.ajax({
//        url: '/mike/api/user/auth/token/accept',
//        type: 'GET',
//        headers: {
//          'Authorization': 'Bearer ' + accessToken
//        },
//        success: function(response) {
//          // Xử lý phản hồi thành công
//          console.log(response);
//        },
//        error: function(xhr, status, error) {
//          window.location.replace('/mike/auth/login');
//        }
//      });
//    }
//  }
//});
