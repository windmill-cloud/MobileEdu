//
//  ViewController.h
//  EnableDisableObjects
//
//  Created by Xuan Wang on 2/8/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segment;
@property (weak, nonatomic) IBOutlet UISwitch *switchOutlet;

- (IBAction)enable:(id)sender;
- (IBAction)disable:(id)sender;

@end

