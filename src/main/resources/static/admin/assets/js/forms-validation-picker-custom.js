$(document).ready(function() {
  $('.picker-valid').each(function() {
    //.select-validation-custom
    var pickerInput = $(this);
    var form = pickerInput.closest('form');
    var wrapper = pickerInput.closest('.wrapper-picker-valid-custom');
    var statusIsInvalidFeedback = wrapper.find('.invalid-feedback');
    var statusIsValidFeedback = wrapper.find('.valid-feedback');
    var formSubmitted = false;

    function validateSelect() {
      var selectValue = pickerInput.val();

      if (selectValue === "") {
        statusIsInvalidFeedback.addClass('d-block');
        pickerInput.addClass('is-invalid');
        statusIsValidFeedback.removeClass('d-block');
        pickerInput.removeClass('is-valid');
      } else {
        statusIsInvalidFeedback.removeClass('d-block');
        pickerInput.removeClass('is-invalid');
        statusIsValidFeedback.addClass('d-block');
        pickerInput.addClass('is-valid');
      }
    }

    pickerInput.change(function() {
      if (formSubmitted) {
        validateSelect();
      }
    });

    form.submit(function(event) {
      validateSelect();
      formSubmitted = true; // Set the flag to true
      if(!form[0].checkValidity()){
        event.preventDefault(); // Prevent form submission
      }
    });
  });
});