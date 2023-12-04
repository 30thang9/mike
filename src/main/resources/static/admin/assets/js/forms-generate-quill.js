function initializeFullEditor(quillClass) {
  var quills = document.querySelectorAll("." + quillClass);

  Array.prototype.forEach.call(quills, function (quill) {
    new Quill(quill, {
      bounds: quill,
      placeholder: "Product Description",
      modules: {
        formula: !0,
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
  });
}

initializeFullEditor('ql-full');

//new Quill(".ql-full", {
//  bounds: ".ql-full",
//  placeholder: "Product Description",
//  modules: {
//    formula: !0,
//    toolbar: [[{
//      font: []
//    }, {
//      size: []
//    }], ["bold", "italic", "underline", "strike"], [{
//      color: []
//    }, {
//      background: []
//    }], [{
//      script: "super"
//    }, {
//      script: "sub"
//    }], [{
//      header: "1"
//    }, {
//      header: "2"
//    }, "blockquote", "code-block"], [{
//      list: "ordered"
//    }, {
//      list: "bullet"
//    }, {
//      indent: "-1"
//    }, {
//      indent: "+1"
//    }], [{
//      direction: "rtl"
//    }], ["link", "image", "video", "formula"], ["clean"]]
//  },
//  theme: "snow"
//});