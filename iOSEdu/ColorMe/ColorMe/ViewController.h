//
//  ViewController.h
//  ColorMe
//
//  Created by Xuan Wang on 2/6/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIButton *redButton;
@property (weak, nonatomic) IBOutlet UIButton *greenButton;
@property (weak, nonatomic) IBOutlet UIButton *blueButton;
- (IBAction)setRed:(id)sender;
- (IBAction)setGreen:(id)sender;
- (IBAction)setBlue:(id)sender;


@end

