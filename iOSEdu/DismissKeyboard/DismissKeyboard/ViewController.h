//
//  ViewController.h
//  DissmissKeyboard
//
//  Created by Xuan Wang on 2/5/17.
//  Copyright © 2017 a. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UITextViewDelegate>{
    
}
@property (weak, nonatomic) IBOutlet UITextView *textView;
@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UITextField *textField;

- (IBAction)setText:(id)sender;

@end

