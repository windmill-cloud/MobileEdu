//
//  ViewController.h
//  TapMeFast
//
//  Created by Xuan Wang on 2/8/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController{
    NSTimer *timer;
    int timeInt;
    int tapInt;
}
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *scoreLabel;

@property (weak, nonatomic) IBOutlet UIButton *tapMeButton;
@property (weak, nonatomic) IBOutlet UIButton *startGameButton;
- (IBAction)tapButton:(id)sender;
- (IBAction)startGame:(id)sender;

@end

