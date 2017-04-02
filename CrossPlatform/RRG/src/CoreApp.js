/**
 * Created by xuanwang on 3/29/17.
 */
import React from 'react';
import { StackNavigator } from 'react-navigation';
import MainScreen from './MainScreen';
import ChatScreen from './ChatScreen';


const CoreApp = StackNavigator({
    Home: { screen: MainScreen },
    Chat: { screen: ChatScreen }

    //Profile: {screen: ProfileScreen},
});

export default CoreApp;