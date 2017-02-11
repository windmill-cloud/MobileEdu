//
//  ViewController.h
//  UsingSegmentControl
//
//  Created by Xuan Wang on 2/10/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentControl;
- (IBAction)segmentAction:(id)sender;

@end

