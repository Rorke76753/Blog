const compressionPlugin = require("compression-webpack-plugin");
const productionGzipExtensions = ["js", "css"];
module.export = {
  build: {
    configureWebpack: {
      plugins: [
        new compressionPlugin({
          filename: "[path].gz[query]",
          algorithm: "gzip",
          test: new RegExp("\\.(" + productionGzipExtensions.join("|") + ")$"),
          threshold: 10240,
          minRatio: 0.8
        })
      ]
    }
  }
};
