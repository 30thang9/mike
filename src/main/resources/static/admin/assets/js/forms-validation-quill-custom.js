function attachQuillEvents(ql) {
    var form = ql.closest('form');
    var formSubmitted = false;

    var qlToolBar = ql.siblings('.ql-toolbar.ql-snow');
    var qlToolBarClasses = qlToolBar.attr('class');

    if (qlToolBar.length > 0) {
      qlToolBar.remove();
    }

    // Initialize Quill editor
    var quill = new Quill(ql.get(0), {
      bounds: ql.get(0),
      placeholder: "Product Description",
      modules: {
        formula: true,
        toolbar: [[{
          font: []
        }, {
          size: []
        }], ["bold", "italic", "underline", "strike"], [{
          color: []
        }, {
          background: []
        }], [{
          script: "super"
        }, {
          script: "sub"
        }], [{
          header: "1"
        }, {
          header: "2"
        }, "blockquote", "code-block"], [{
          list: "ordered"
        }, {
          list: "bullet"
        }, {
          indent: "-1"
        }, {
          indent: "+1"
        }], [{
          direction: "rtl"
        }], ["link", "image", "video", "formula"], ["clean"]]
      },
      theme: "snow"
    });

    ql.siblings('.ql-toolbar.ql-snow').attr('class', qlToolBarClasses);

    quill.on('text-change', function (delta, oldDelta, source) {
      if (formSubmitted) {
        handleValidateQuill(ql);  // Pass the Quill instance
      }
    });

    form.submit(function(event) {
        handleValidateQuill(ql);
        formSubmitted = true ;
        if (ql.find('.ql-editor').eq(0).hasClass('ql-blank')) {
            event.preventDefault();
        }
    });
}

function handleValidateQuill(editor) {
    const qlEdit = editor.find('.ql-editor').eq(0);
    const qlToolBar = editor.siblings('.ql-toolbar');
    const parent = editor.parent();
    const validFeedback = parent.siblings('.valid-feedback');
    const invalidFeedback = parent.siblings('.invalid-feedback');

    if (qlEdit.hasClass('ql-blank')) {
        editor.addClass('ql-border is-invalid');
        qlToolBar.addClass('ql-border is-invalid');
        editor.removeClass('is-valid');
        qlToolBar.removeClass('is-valid');
        invalidFeedback.addClass('d-block');
        validFeedback.removeClass('d-block');
    } else {
        editor.addClass('ql-border is-valid');
        qlToolBar.addClass('ql-border is-valid');
        editor.removeClass('is-invalid');
        qlToolBar.removeClass('is-invalid');
        validFeedback.addClass('d-block');
        invalidFeedback.removeClass('d-block');
    }
}

$(document).ready(function() {
    $('.ql-full').each(function() {
        attachQuillEvents($(this));
    });
});
