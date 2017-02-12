//
//  ViewController.h
//  UsingSliders
//
//  Created by Xuan Wang on 2/11/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UISlider *slider;
@property (weak, nonatomic) IBOutlet UILabel *fontValLabel;
- (IBAction)fontSize:(id)sender;


@end

