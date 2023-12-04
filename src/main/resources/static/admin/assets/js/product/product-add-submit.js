$(document).ready(function () {
    var myForm = document.getElementById('myForm');

    myForm.addEventListener('submit', function (event) {
        event.preventDefault();
        var dropzoneElement = myForm.querySelector('[data-name="avatar"].dropzone-single');
        var avatarFile = dropzoneElement && dropzoneElement.dropzone ? dropzoneElement.dropzone.files[0] : null;
        var isValidImage = true;
        var dropzoneImages = myForm.querySelectorAll('[id^="dropzone-basic-"].dropzone.dropzone-multiple');
        if (dropzoneImages.length > 0) {
            dropzoneImages.forEach(function (dropzone, index) {
                var files = dropzone.dropzone.files;
                if (files.length <= 0) {
                    isValidImage = false;
                }
            });
        } else {
            isValidImage = false;
        }

        if (avatarFile && isValidImage && myForm.checkValidity()) {

            var formData = new FormData();

            var product = {
                name: $('input[name="name"].form-control').eq(0).val() || '',
                description: $('#full-editor .ql-editor:first').html() || '',
                avatarFile: avatarFile,
                avatar: avatarFile.name,
                objectCategoryId: $('select[name="objectCategory"].select2.form-select').eq(0).val() || '',
                productCategoryId: $('select[name="productCategory"].select2.form-select').eq(0).val() || '',
                sizeImageId: $('select[name="sizeImage"].select2.form-select').eq(0).val() || '',
                productStatus: $('select[name="productStatus"].select2.form-select').eq(0).val() || '',
                hotStatus: $('select[name="hotStatus"].select2.form-select').eq(0).val() || '',
                bestSellerStatus: $('select[name="bestSellerStatus"].select2.form-select').eq(0).val() || '',
            };

            for (var key in product) {
                if (product[key] instanceof File) {
                    formData.append('product.' + key, product[key], product[key].name);
                } else {
                    formData.append('product.' + key, product[key]);
                }
            }

            var productDetails = [];

            dropzoneImages.forEach(function (dropzone, index) {
                var files = dropzone.dropzone.files;

                var sizeSelector = $('select[data-name="size"][id^="form-repeater-"].select2.form-select').eq(index);
                var selectedData = sizeSelector.select2('data');
                var sizeIds = $.map(selectedData, function (option) {
                    return option.id;
                });

                var productDetail = {
                    colorId: $('select[data-name="color"][id^="form-repeater-"].select2.form-select').eq(index).val() || '',
                    sizeIds: sizeIds,
                    importPrice: $('input[data-name="importPrice"][id^="form-repeater-"].form-control').eq(index).val() || '',
                    exportPrice: $('input[data-name="exportPrice"][id^="form-repeater-"].form-control').eq(index).val() || '',
                    imageFiles: [],
                    images: [],
                    discountPercent: $('input[data-name="discountPercent"][id^="form-repeater-"].form-control').eq(index).val() || '',
                    productDetailStatus: $('select[data-name="productDetailStatus"][id^="form-repeater-"].select2.form-select').eq(index).val() || ''
                };

                files.forEach(function (file) {
                    if (file instanceof File) {
                        productDetail.imageFiles.push(file);
                        productDetail.images.push(file.name);
                    }
                });

                productDetails.push(productDetail);

                formData.append('productDetails[' + index + '].colorId', productDetail.colorId);
                formData.append('productDetails[' + index + '].sizeIds', productDetail.sizeIds.join(','));
                formData.append('productDetails[' + index + '].importPrice', productDetail.importPrice);
                formData.append('productDetails[' + index + '].exportPrice', productDetail.exportPrice);
                formData.append('productDetails[' + index + '].discountPercent', productDetail.discountPercent);
                formData.append('productDetails[' + index + '].productDetailStatus', productDetail.productDetailStatus);
                formData.append('productDetails[' + index + '].images', productDetail.images.join(','));

                if (productDetail.images.length > 0) {
                    productDetail.imageFiles.forEach(function (file, fileIndex) {
                        formData.append('productDetails[' + index + '].imageFiles[' + fileIndex + ']', file, file.name);
                    });
                }

            });

            var url = myForm.getAttribute('action');

            var accessToken = localStorage.getItem('accessToken');

            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                //                headers: {
                //                  "Authorization": "Bearer " + accessToken
                //                },
                success: function (response) {
                    showModal("Add success!", true);
                },
                error: function (xhr, status, error) {
                    var errorMessage = "Add error: ";
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        errorMessage += xhr.responseJSON.message;
                    } else {
                        errorMessage += error;
                    }
                    showModal(errorMessage, false);
                }
            });
        }
    });

    function showModal(message, isSuccess) {
        var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
        var modalBody = $("<div class='modal-body'></div>").text(message);
        var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
        var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
        var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
        var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
        var modal = $("<div class='modal fade'></div>").append(modalDialog);

        modal.on("hidden.bs.modal", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.remove();
        });

        closeButton.on("click", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.modal("hide");
        });

        $("body").append(modal);
        modal.modal("show");
    }
});