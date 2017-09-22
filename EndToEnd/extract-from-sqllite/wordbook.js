var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('./vocab.db');
var fs = require('fs');

var wordbookfile = __dirname + '/SortedWordbook.txt';
// if (!fs.existsSync(wordbookfile)) {
// }
fs.closeSync(fs.openSync(wordbookfile, 'w'));

var count = 0;

function done(){
  console.log('=================================');
  console.log('Words Imported from Kindle: ' + count);
  console.log('=================================');
}


db.each('SELECT * FROM WORDS', function(err, row) {
  var word = row.word;
  // console.log(word);
  if(word.charAt(0).match(/[a-z]/i)){
    fs.appendFile(wordbookfile, word + '\n', (err) => {
      if (err) throw err;
    });
    count++;
  }
}, done);
