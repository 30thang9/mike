function handleValidateDropzone(myDropzone, dropzone) {
    var validFeedback = dropzone.siblings('.valid-feedback');
    var invalidFeedback = dropzone.siblings('.invalid-feedback');
    var hasFile = myDropzone.files.length > 0;

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

    return hasFile; // Return the value of hasFile
}

function attachDropzoneEvents(dropzone) {
    var myDropzone = Dropzone.forElement(dropzone[0]);
    var form = dropzone.closest('form');
    var formSubmitted = false;
    var hasFile = false; // Declare hasFile here

    myDropzone.on("addedfile", function(file) {
        if (formSubmitted) {
            hasFile = handleValidateDropzone(myDropzone, dropzone);
        }
    });

    myDropzone.on("removedfile", function(file) {
        if (formSubmitted) {
            hasFile = handleValidateDropzone(myDropzone, dropzone);
        }
    });

    form.on('submit', function(event) {
        hasFile = handleValidateDropzone(myDropzone, dropzone);
        formSubmitted = true;

        if (hasFile) {
          //form[0].submit(); // Gửi form
        } else {
          event.preventDefault(); // Prevent form submission
        }
    });
}

$(document).ready(function() {
  $('.dropzone').each(function() {
    attachDropzoneEvents($(this));
  });
});
