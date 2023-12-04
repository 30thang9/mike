"use strict";

function initializeDropzoneSingle(dropzoneClass, previewTemplate) {
  var dropzones = document.querySelectorAll("." + dropzoneClass);

  Array.prototype.forEach.call(dropzones, function (dropzone) {
    new Dropzone(dropzone, {
      url: "/upload",
      previewTemplate: previewTemplate,
      parallelUploads: 1,
      maxFilesize: 5,
      addRemoveLinks: true,
      maxFiles: 1,
      init: function () {
        this.on("addedfile", function (file) {
          if (this.files.length > 1) {
            // Nếu đã có một tệp tin khác được tải lên trước đó, xóa tệp tin đó
            this.removeFile(this.files[0]);
          }
        });
      }
    });
  });
}

function initializeDropzoneMultiple(dropzoneClass, previewTemplate) {
  var dropzones = document.querySelectorAll("." + dropzoneClass);

  Array.prototype.forEach.call(dropzones, function (dropzone) {
    new Dropzone(dropzone, {
      url: "/upload",
      previewTemplate: previewTemplate,
      parallelUploads: 1,
      maxFilesize: 5,
      addRemoveLinks: true,
      init: function () {
        this.on("addedfile", function (file) {
          //          if (this.files.length > this.options.maxFiles) {
          //            var removedFile = this.files.pop();
          //            this.removeFile(removedFile);
          //          }
        });
      }
    });
  });
}

function initializeDropzoneSingleUpload(dropzoneClass, previewTemplate) {
  var dropzones = document.querySelectorAll("." + dropzoneClass);

  Array.prototype.forEach.call(dropzones, function (dropzone) {
    var fileName = dropzone.dataset.imageName;
    var imageUrlApi = "/mike/api/images/product/" + fileName;
    console.log(imageUrlApi);

    var myDropzone = new Dropzone(dropzone, {
      url: "/upload",
      previewTemplate: previewTemplate,
      parallelUploads: 1,
      maxFilesize: 5,
      addRemoveLinks: true,
      maxFiles: 1,
      init: function () {

        this.on("addedfile", function (file) {
          if (this.files.length > 1) {
            this.removeFile(this.files[0]);
          }
        });

        addFileToDropzone(this, imageUrlApi, fileName);

      }
    });
  });

  function addFileToDropzone(dropzone, apiUrl, fileName) {
    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error(`Failed to fetch file. HTTP status ${response.status}`);
        }
        return response.blob();
      })
      .then(blob => {
        // Convert the Blob to a File
        var file = new File([blob], fileName, { type: blob.type });

        // Add the file to Dropzone
        dropzone.addFile(file);
        console.log('File added successfully:', file);
      })
      .catch(error => {
        console.error('Error fetching file from API:', error);
      });
  }

}


var previewTemplate = `<div class="dz-preview dz-file-preview">
  <div class="dz-details">
    <div class="dz-thumbnail">
      <img data-dz-thumbnail>
      <span class="dz-nopreview">No preview</span>
      <div class="dz-success-mark"></div>
      <div class="dz-error-mark"></div>
      <div class="dz-error-message"><span data-dz-errormessage></span></div>
      <div class="progress">
        <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuemin="0" aria-valuemax="100" data-dz-uploadprogress></div>
      </div>
    </div>
    <div class="dz-filename" data-dz-name></div>
    <div class="dz-size" data-dz-size></div>
  </div>
</div>`;

$(document).ready(function () {
  initializeDropzoneSingle("dropzone-single", previewTemplate);

  initializeDropzoneMultiple("dropzone-multiple", previewTemplate);

  initializeDropzoneSingleUpload("dropzone-single-upload", previewTemplate);

});
