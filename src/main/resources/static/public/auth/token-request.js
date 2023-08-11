// document.addEventListener("DOMContentLoaded", function () {
//     if (window.location.href.startsWith('http://localhost:8080/mike/admin') || window.location.href.startsWith('http://localhost:8080/mike/user/profile')) {
//         var accessToken = localStorage.getItem('accessToken');
//         //    var currentUrl=window.location.href;
//         if (!accessToken) {
//             window.location.replace('/mike/auth/login');
//         } else {
//             $.ajax({
//                 url: '/mike/api/user/auth/token/accept',
//                 type: 'GET',
//                 headers: {
//                     'Authorization': 'Bearer ' + accessToken
//                 },
//                 success: function (response) {
//                     console.log(response);
//                 },
//                 error: function (xhr, status, error) {
//                     if (status === 403) {
//                         var refreshTokenUrl = "/mike/api/user/auth/token/refresh";
//                         refreshToken(refreshTokenUrl);
//                     }
//                 }
//             });
//         }
//     }

//     function refreshToken(url) {
//         var refreshToken = localStorage.getItem('refreshToken');
//         if (!refreshToken) {
//             window.location.replace('/mike/auth/login');
//         } else {
//             $.ajax({
//                 url: url,
//                 type: 'GET',
//                 headers: {
//                     'Authorization': 'Bearer ' + refreshToken
//                 },
//                 success: function (response) {
//                     console.log(response);
//                     var newAccessToken = response.accessToken;
//                     var newRefreshToken = response.refreshToken;
//                     localStorage.setItem('accessToken', newAccessToken);
//                     localStorage.setItem('refreshToken', newRefreshToken);
//                 },
//                 error: function (xhr, status, error) {
//                     if (status === 403) {
//                         window.location.replace('/mike/auth/login');
//                     }
//                 }
//             });
//         }
//     }
// });
