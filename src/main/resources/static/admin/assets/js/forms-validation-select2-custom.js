function handleValidateSelect2(input) {
    const spanSibling = input.siblings('span.select2.select2-container');
    const parent = input.closest('div');
    const validFeedback = parent.siblings('.valid-feedback');
    const invalidFeedback = parent.siblings('.invalid-feedback');
    const spanSelectionType = spanSibling.find('.selection').eq(0).find('.select2-selection').eq(0);
    let isValid = false;

    if (spanSelectionType.hasClass('select2-selection--multiple')) {
        isValid = spanSelectionType.find('.select2-selection__choice').length > 0;
    } else if (spanSelectionType.hasClass('select2-selection--single')) {
        isValid = input.val();
    }
    if(!input.hasClass('prevent-validate')){
        if (!isValid) {
            spanSibling.addClass('is-invalid');
            spanSibling.removeClass('is-valid');
            invalidFeedback.addClass('d-block');
            validFeedback.removeClass('d-block');
        } else {
            spanSibling.addClass('is-valid');
            spanSibling.removeClass('is-invalid');
            validFeedback.addClass('d-block');
            invalidFeedback.removeClass('d-block');
        }
    }
}

function attachSelectEvents(selectInput) {
  var form = selectInput.closest('form');
  var formSubmitted = false;

  selectInput.change(function() {
    if (formSubmitted) {
      handleValidateSelect2(selectInput);
    }
  });

  form.submit(function(event) {
    handleValidateSelect2(selectInput);
    formSubmitted = true;
    if (!form[0].checkValidity()) {
      event.preventDefault();
    }
  });
}


$(document).ready(function() {
  $('select.select2').each(function() {
    attachSelectEvents($(this));
  });
});
