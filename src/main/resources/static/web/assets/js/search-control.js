$(document).ready(function () {
    $('#search-area-form').on('submit', function (e) {
        e.preventDefault();
        var value = $(this).find('#search-area-input').val();
        if (value) {
            value = value.trim().replace(/\s+/g, "+");
            window.location.href = "/mike/collection/shop/shop-search/" + value;
        }
    });
});
