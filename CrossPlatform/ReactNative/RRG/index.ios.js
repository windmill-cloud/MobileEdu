/**
 * React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React from 'react';
import {
    AppRegistry,
} from 'react-native';
import CoreApp from './src/CoreApp';

const App = () => {
  return (<CoreApp />);
};

AppRegistry.registerComponent('RRG', () => App);
