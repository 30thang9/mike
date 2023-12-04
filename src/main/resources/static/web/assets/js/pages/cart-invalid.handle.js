$(document).ready(function() {
    $('.remove-cart-item').on('click', function(){
        alertConfirmDelete($(this));
    })

    $('.update-cart-item').on('click', function(){
        alertConfirmUpdate($(this));
    })

    function updateItem($btn) {
        var productId = $btn.closest('div.cart-item').find('.product-name').eq(0).data('product-id');
        var sizeId = $btn.closest('div.cart-item').find('.size-name').eq(0).data('size-id');
        var colorId = $btn.closest('div.cart-item').find('.color-name').eq(0).data('color-id');
        var quantity = $btn.closest('div.cart-item').find('.cart-item-limitQuantity').eq(0).text();
        var price = $btn.closest('div.cart-item').find('.cart-item-price').eq(0).text();

        var formData =
        {
            id:
            {
                productId: productId,
                sizeId: sizeId,
                colorId: colorId
            },
            quantity: quantity,
            price: price
        }

        var url = "/mike/api/cart/update";
        $.ajax({
            url: url,
            type: 'PUT',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(data) {
                showAlert("Update item successfully!",true);
            },
            error: function(xhr, status, error) {
                showAlert("Update item failed!",false);
            }
        });
    }

    function deleteItem($btn){
        var productId = $btn.closest('div.cart-item').find('.product-name').eq(0).data('product-id');
        var sizeId = $btn.closest('div.cart-item').find('.size-name').eq(0).data('size-id');
        var colorId = $btn.closest('div.cart-item').find('.color-name').eq(0).data('color-id');

        var formData =
        {
            productId: productId,
            sizeId: sizeId,
            colorId: colorId
        }

        var url = "/mike/api/cart/delete";
        $.ajax({
            url: url,
            type: 'DELETE',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(data) {
                showAlert("Delete item successfully!",true);
            },
            error: function(xhr, status, error) {
                showAlert("Delete item failed!",false);
            }
        });
    }

    function alertConfirmUpdate($btn){
        Swal.fire({
            title: 'Confirmation',
            text: 'Are you want to update this item?',
            icon: 'question',
            showCancelButton: true,
            cancelButtonText: 'Cancel',
            confirmButtonText: 'Ok',
            customClass: {
                confirmButton: 'btn btn-primary',
                cancelButton: 'btn btn-secondary'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                updateItem($btn);
            }
        });
    }

    function alertConfirmDelete($btn){
            Swal.fire({
                title: 'Confirmation',
                text: 'Are you want to delete this item?',
                icon: 'question',
                showCancelButton: true,
                cancelButtonText: 'Cancel',
                confirmButtonText: 'Ok',
                customClass: {
                    confirmButton: 'btn btn-primary',
                    cancelButton: 'btn btn-secondary'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    deleteItem($btn);
                }
            });
        }

    function showAlert(message, isSuccess) {
        Swal.fire({
            title: message,
            icon: isSuccess ? 'success' : 'error',
            timer: 1200,
            timerProgressBar: true,
            showConfirmButton: false,
            customClass: {
                timerProgressBar: isSuccess ? 'bg-success' : 'bg-danger'
            },
            willClose: () => {
                if (isSuccess) {
                    location.reload();
                }
            }
        });
    }

})