$(document).ready(function() {
    $('.dropzone').each(function() {
      var dropzone = $(this);
      var form = dropzone.closest('form');
      var wrapper = dropzone.closest('.wrapper-dropzone-valid-custom');
      var validFeedback = wrapper.find('.valid-feedback');
      var invalidFeedback = wrapper.find('.invalid-feedback');
//      var inputHiddenUpload = wrapper.find('.input-hidden-upload');
      var formSubmitted = false;
      var hasFile = false;

      // Sử dụng biến myDropzone đã tồn tại
      var myDropzone = Dropzone.forElement(dropzone[0]);

      // Xác định lại sự kiện "addedfile" cho myDropzone
      myDropzone.on("addedfile", function(file) {
        if(formSubmitted){
          validateDropzone();
        }
      });

      // Xác định lại sự kiện "removedfile" cho myDropzone
      myDropzone.on("removedfile", function(file) {
        if(formSubmitted){
          validateDropzone();
        }
      });

      function validateDropzone() {
        hasFile = myDropzone.files.length > 0;

        if (hasFile) {
          validFeedback.addClass('d-block');
          invalidFeedback.removeClass('d-block');
          dropzone.addClass('border-dashed border-success');
          dropzone.removeClass('border-dashed border-danger');
        } else {
          validFeedback.removeClass('d-block');
          invalidFeedback.addClass('d-block');
          dropzone.removeClass('border-dashed border-success');
          dropzone.addClass('border-dashed border-danger');
        }
      }

      form.on('submit', function(event) {
        validateDropzone();
        formSubmitted = true; // Set the flag to true

        if (form[0].checkValidity() && hasFile) {
          //form[0].submit(); // Gửi form
        }else{
          event.preventDefault(); // Prevent form submission
        }
      });
    });
});