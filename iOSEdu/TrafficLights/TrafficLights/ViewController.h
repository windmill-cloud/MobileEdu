//
//  ViewController.h
//  TrafficLights
//
//  Created by Xuan Wang on 2/7/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController{
    NSTimer *timer;
    NSTimer *scoreTimer;
    BOOL first;
    int timerInt;
    int scoreInt;
}
@property (weak, nonatomic) IBOutlet UIImageView *trafficLight;
@property (weak, nonatomic) IBOutlet UILabel *scoreLabel;
@property (weak, nonatomic) IBOutlet UIButton *startButton;
- (IBAction)startStop:(id)sender;

@end

