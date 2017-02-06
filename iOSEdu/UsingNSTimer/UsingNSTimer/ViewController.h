//
//  ViewController.h
//  UsingNSTimer
//
//  Created by Xuan Wang on 2/6/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    NSTimer *timer;
    int countInt;
}

@property (weak, nonatomic) IBOutlet UILabel *label;
- (IBAction)startTimer:(id)sender;
- (IBAction)stopTimer:(id)sender;


@end

