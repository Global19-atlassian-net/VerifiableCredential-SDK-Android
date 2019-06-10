const path = require('path');

module.exports = {
  devtool: 'inline-source-map',
  entry: './src/index.ts',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname),
    // library: 'UserAgent',
    libraryTarget: 'commonjs'
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.node']
  },
  optimization: {
    minimize: false
  },
  node: {
    fs: 'empty',
    crypto: true,
  },
  externals: [
     "Buffer"
  ],
  target: 'web',
  module: {
    noParse: /node\-webcrypto\-ossl/,
    rules: [
      { test: /\.ts$/,
        use: [
          {
            loader: 'ts-loader',
            options: {
              compilerOptions: {
                lib: ['es2018', 'dom']
              }
            }
          },
        ],
        exclude: [
          path.resolve(__dirname, "./node_modules/webcrypto-core",),
          path.resolve(__dirname, "./node_modules/node-webcrypto-ossl"),
        ]
      },
      { test: /\.node$/, use: 'node-loader' }
    ]
  }
};
