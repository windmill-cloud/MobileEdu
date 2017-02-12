//
//  ViewController.h
//  HttpRequests
//
//  Created by Xuan Wang on 2/11/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UITextField *textInput;
@property (weak, nonatomic) IBOutlet UITextView *contentView;
- (IBAction)getContent:(id)sender;

@end

