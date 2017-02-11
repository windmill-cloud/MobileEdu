//
//  ViewController.h
//  DigitalClock
//
//  Created by Xuan Wang on 2/10/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    NSTimer *timer;
}
@property (weak, nonatomic) IBOutlet UILabel *label;

@property (weak, nonatomic) IBOutlet UIView *settingView;

@property (weak, nonatomic) IBOutlet UIButton *settingButton;

@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentClock;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentBackground;

- (IBAction)clockColor:(id)sender;
- (IBAction)backgroundColor:(id)sender;
- (IBAction)setting:(id)sender;

@end

