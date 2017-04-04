/**
 * Created by xuanwang on 4/1/17.
 */
import React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationComponent } from 'react-native-material-bottom-navigation'
import { TabNavigator } from 'react-navigation'
import Icon from 'react-native-vector-icons/MaterialIcons'

class MoviesAndTV extends React.Component {
    static navigationOptions = {
        tabBar: {
            label: 'Movies & TV',
            icon: () => (<Icon size={24} color="white" name="tv" />)
        }
    };

    render() {
        return (
            <View>
                <Text>
                    Movies and TV
                </Text>
            </View>
        );
    }
}

class Music extends React.Component {
    static navigationOptions = {
        tabBar: {
            label: 'Music',
            icon: () => (<Icon size={24} color="white" name="music-note" />)
        }
    };

    render() {
        return (
            <View>
                <Text>
                    Music
                </Text>
            </View>
        );
    }
}

class Newsstand extends React.Component {
    static navigationOptions = {
        tabBar: {
            label: 'Newsstand',
            icon: () => (<Icon size={24} color="white" name="nature" />)
        }
    };

    render() {
        return (
            <View>
                <Text>
                    nature
                </Text>
            </View>
        );
    }
}

const MainScreen = TabNavigator({
    MoviesAndTV: { screen: MoviesAndTV },
    Music: { screen: Music },
    Newsstand: { screen: Newsstand }
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
                Newsstand: {
                    barBackgroundColor: '#EEEEEE',
                    labelColor: '#434343', // like in the standalone version, this will override the already specified `labelColor` for this tab
                    activeLabelColor: '#212121',
                    activeIcon: <Icon size={24} color="#212121" name="newsstand" />
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