# Covid-Projects
This repository includes some of the computer vision related projects I had done on covid CT images. 
## The code is extreamly nasty!!!!
Eventhough pre-built libraries and straight forward methods could be used for satisfying the needs, I was more interested in learning new things and doing everything on a unusual methodolgoy.
So, the code may feel like a garbage. :D
### Inspiration
The data handling methods and the evaluation metrics were inspired from the **AI4MED** Course by [deeplearning.ai](deeplearning.ai)
#### A little intro to files;
* Covid-Ct : Android app with much cool animations and design (according to me though, Am not into front-end development and design and this is my first work on that field :D), which also require a bunch of UI/UX improvements.
* test-covid : With a basic !Design. Ya, I didn't cared about the design part in this android app. Predecissor of Covid-Ct with tesnroflow-lite support. I will remove this app once am satisfied with my Covid-CT app.

(*The android apps are powered by kotlin :)*)
* a_bunch_of_ipynb's
	* Covid-Ct.ipynb : Trying out different pre-trained models like VGG16, VGG19, Resnet etc for the problem.
	* BiT-Radiology: A test try and a nasty experiment over the Big Transfer pretrained model.
	* tflite-covid.ipynb: contains the tflite conversion of the fine-tuned model and some basic inferences. 
	
	On the ipynb notebooks, I had tried to include a variety of evaluation metrics and also tried to use ideas like progressive resizing, learning rate finder, cycling learning rate, etc.
	
	
