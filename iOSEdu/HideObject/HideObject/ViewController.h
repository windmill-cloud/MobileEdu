//
//  ViewController.h
//  HideObject
//
//  Created by Xuan Wang on 2/8/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentSwitch;
@property (weak, nonatomic) IBOutlet UISwitch *switchOutlet;
- (IBAction)hide:(id)sender;
- (IBAction)reveal:(id)sender;

@end

