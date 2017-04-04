/**
 * Created by xuanwang on 4/1/17.
 */
import React from 'react';
import { View, Text } from 'react-native';
import { NavigationComponent } from 'react-native-material-bottom-navigation'
import { TabNavigator } from 'react-navigation'
import Icon from 'react-native-vector-icons/MaterialIcons'
import MoviesAndTV from './tabs/MovieAndTVTab';
import Music from './tabs/MusicTab';
import Podcasts from './tabs/Podcasts';


const MainScreen = TabNavigator({
    MoviesAndTV: { screen: MoviesAndTV },
    Music: { screen: Music },
    Podcasts: { screen: Podcasts }
}, {
    tabBarComponent: NavigationComponent,
    tabBarPosition: 'bottom',
    tabBarOptions: {
        bottomNavigationOptions: {
            labelColor: 'white',
            rippleColor: 'white',
            tabs: {
                MoviesAndTV: {
                    barBackgroundColor: '#37474F'
                },
                Music: {
                    barBackgroundColor: '#00796B'
                },
                Podcasts: {
                    barBackgroundColor: '#8F424F',
                    labelColor: '#ffffff', // like in the standalone version, this will override the already specified `labelColor` for this tab
                    activeLabelColor: '#ffffff',
                    activeIcon: <Icon size={24} color="white" name="radio" />
                }
            }
        }
    }
});

const styles = {
    containerStyle: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'space-around',
    }
};

export default MainScreen;