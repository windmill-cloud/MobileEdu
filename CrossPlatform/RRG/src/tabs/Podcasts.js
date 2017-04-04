/**
 * Created by xuanwang on 4/4/17.
 */
import React from 'react';
import { View, Text } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons'

class Podcasts extends React.Component {
    static navigationOptions = {
        title: "Podcasts",
        tabBar: {
            label: 'Podcasts',
            icon: () => (<Icon size={24} color="white" name="radio" />)
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

export default Podcasts;