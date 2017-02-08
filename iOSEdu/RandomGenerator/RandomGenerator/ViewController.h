//
//  ViewController.h
//  RandomGenerator
//
//  Created by Xuan Wang on 2/8/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UILabel *label2;
- (IBAction)randomNumber:(id)sender;
- (IBAction)randomWord:(id)sender;


@end

