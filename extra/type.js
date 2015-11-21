function winTypeRacer(){
  var words = $('div.cw-QuotePanel-textToTypePanel').text().split(' ');
  typeWords(words, 150);
}

function typeWords(words, wpm) {
  $('.cw-TypedTextBox').val(words.shift()).keydown(function(e) {
    typeWords(words, wpm);
  });
}

setTimeout(winTypeRacer, 3000)
