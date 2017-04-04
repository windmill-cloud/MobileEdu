/**
 * Created by xuanwang on 4/4/17.
 */
import React from 'react';
import { View, Text } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons'

class MoviesAndTV extends React.Component {
    static navigationOptions = {
        title: "Movies and TV",
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

export default MoviesAndTV;