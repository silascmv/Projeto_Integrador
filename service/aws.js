const AWS = require('aws-sdk');

AWS.config.update({accessKeyId: 'OKWG6FE93FDE9D1SYDBV', secretAccessKey: 'UfstV008KY5XOZGY4oKVnzfVi5UsnLyNhgHEVWXo'});
const endpoint = new AWS.Endpoint('cellar-c2.services.clever-cloud.com');
const s3 = new AWS.S3({ endpoint });

s3.listBuckets(function(err, res) {

    console.log(res);
    var url = s3.getSignedUrl('getObject', {Bucket: 'bucketapi', Key: 'OKWG6FE93FDE9D1SYDBV'})
    console.log(url);
  // handle results
});

/* In order to share access to access non-public files via HTTP, you need to get a presigned url for a specific key
 * the example above present a 'getObject' presigned URL. If you want to put a object in the bucket via HTTP,
 * you'll need to use 'putObject' instead.
 * see doc : http://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/S3.html#getSignedUrl-property
 */
