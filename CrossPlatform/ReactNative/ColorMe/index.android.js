/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React from 'react';
import { AppRegistry } from 'react-native';
import ColorMe from './js/ColorMe';

// Create a component
const App = () => (
    <ColorMe />
);

// Render it to the device
AppRegistry.registerComponent('ColorMe', () => App);
