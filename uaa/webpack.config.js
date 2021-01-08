'use strict';

const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const envKeys = Object.keys(process.env).reduce((prev, next) => {
  prev[`process.env.${next}`] = JSON.stringify(process.env[next]);
  return prev;
}, {});

console.log(envKeys);

module.exports = {
  mode: process.env.CI  === 'true' ? 'production' : 'development',
  devtool: 'cheap-module-source-map',
  entry: {
    'index': './src/main/javascript/index.js',
  },
  output: {
    path: path.resolve('./build/tmp'),
  },
  optimization: {
    noEmitOnErrors: true,
    minimize: process.env.CI === 'true',
    concatenateModules: true,
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: 'css/[name].css',
    }),
    new webpack.DefinePlugin(envKeys),
  ],
  module: {
    rules: [
      {
        oneOf: [
          {
            test: /\.(js)$/,
            exclude: /node_modules/,
            loader: 'babel-loader',
          },
          {
            test: /\.(sa|sc|c)ss$/,
            use: [
              {
                loader: MiniCssExtractPlugin.loader,
                options: {
                  sourceMap: true,
                },
              },
              'css-loader',
              'sass-loader',
            ],
          },
          {
            loader: 'file-loader',
            exclude: [/\.(js|jsx)$/, /\.html$/, /\.json$/],
            options: {
              name: 'media/[name].[hash:8].[ext]',
            },
          },
        ],
      },
    ],
  },
};