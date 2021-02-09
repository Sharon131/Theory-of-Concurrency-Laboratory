var fs = require('fs')
var glob = require('glob')

console.time('synch');

var count = 0

const files = glob.sync('./PAM08/**/*')
const filesIterator = files.entries()
console.log(files.length)

function readFromFile(file, onEnd) {
    if (fs.lstatSync(file).isDirectory()) {
        onEnd()
        return
    }
    fs.createReadStream(file).on('data', function (chunk) {
        count += chunk.toString('utf8')
            .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
            .length - 1;
    }).on('end', function () {
        // console.log(file, count);
        onEnd()
    }).on('error', function (err) {
        console.error(err);
        onEnd()
    });
}

function work() {
    const next = filesIterator.next()
    if (!next.done) {
        readFromFile(next.value[1], work)
    } else {
        console.timeEnd('synch');
    }
}

work()