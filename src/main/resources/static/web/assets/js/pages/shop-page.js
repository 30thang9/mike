var snapSlider = document.getElementById('slider-snap');

noUiSlider.create(snapSlider, {
    start: [ 0.0, 2500000.0 ],
    snap: false,
    connect: true,
      step: 10000.0,
    range: {
        'min': 0.0,
        'max': 2500000.0
    }
});
var snapValues = [
    document.getElementById('slider-snap-value-lower'),
    document.getElementById('slider-snap-value-upper')
];
var inputValues = [
    document.getElementById('slider-snap-input-lower'),
    document.getElementById('slider-snap-input-upper')
];
snapSlider.noUiSlider.on('update', function( values, handle ) {
    snapValues[handle].innerHTML = values[handle];
});

//snapSlider.noUiSlider.on('change', function( values, handle ) {
//  inputValues[handle].value = values[handle];
//  $(inputValues[handle]).change();
//});

snapSlider.noUiSlider.on('change', function(values) {
  inputValues.forEach(function(input, index) {
    input.value = values[index];
    $(input).change();
  });
});