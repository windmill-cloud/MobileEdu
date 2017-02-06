//
//  ViewController.h
//  SetProperties
//
//  Created by Xuan Wang on 2/5/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *label;
- (IBAction)setColor:(id)sender;
- (IBAction)setBackground:(id)sender;
- (IBAction)setFontSize:(id)sender;
- (IBAction)setShadow:(id)sender;
- (IBAction)setShadowColor:(id)sender;
- (IBAction)setLeft:(id)sender;
- (IBAction)setRight:(id)sender;
- (IBAction)setCenter:(id)sender;

@end

