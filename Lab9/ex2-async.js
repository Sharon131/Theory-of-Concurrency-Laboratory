//find . -name '*' | xargs wc -l | sort -nr
var fs = require('fs')
var glob = require('glob')
var fsPromises = require('fs').promises
var lodash = require('lodash')
var async = require("async");


console.time('asynch');
var count = 0

async function readFromFile(file) {
    return new Promise((resolve, reject) => {
        fsPromises.lstat(file).then((lstat) => {
            if (lstat.isDirectory()) {
                resolve()
            } else {
                fs.createReadStream(file).on('data', function (chunk) {
                    count += chunk.toString('utf8')
                        .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
                        .length - 1;
                }).on('end', function () {
                    // console.log(file, count);
                    resolve()
                }).on('error', function (err) {
                    console.error(err);
                    reject()
                });
            }
        })
    })
}
// 'C:/Python27/**/*'
glob('./PAM08/**/*', (err, files) => {
    console.log(files.length)
    const executions = []
    for(let file of files){
        executions.push((callback) => readFromFile(file).then(() => callback()))
    }
    //Limited version due to the amount of files that the os is capable of opening
    async.parallelLimit(executions, 100, (a, b) => {
        console.timeEnd('asynch');
        console.log('done')
    })
})
