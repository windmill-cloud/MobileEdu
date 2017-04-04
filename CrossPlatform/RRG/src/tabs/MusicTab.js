/**
 * Created by xuanwang on 4/4/17.
 */

import React from 'react';
import { View, Text } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons'

class Music extends React.Component {
    static navigationOptions = {
        title: "Music",
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

export default Music;