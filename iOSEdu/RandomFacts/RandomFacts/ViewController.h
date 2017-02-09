//
//  ViewController.h
//  RandomFacts
//
//  Created by Xuan Wang on 2/8/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *label;
- (IBAction)randomFact:(id)sender;

@end

