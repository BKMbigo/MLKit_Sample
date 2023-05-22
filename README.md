# ML Kit Sample Application
### Introduction
Welcome to the ML Kit Sample Application! This application is designed to showcase the powerful features of [Google's ML Kit library](https://developers.google.com/ml-kit). While the project aims to include all features provided by the library, the current focus is on text processing capabilities, including Language Identification, Translation, Smart Reply, and Entity Extraction. Whether you're a developer exploring ML Kit or simply curious about its potential, this sample application will provide you with a hands-on experience to delve into the world of ML Kit.

## ML Kit features

### Text Features
#### [Language Identification](https://developers.google.com/ml-kit/language/identification)
The Language Identification feature enables you to identify the language used in a given text. Leveraging ML Kit's advanced language recognition models, this feature accurately detects over 100 different languages. Whether you're building multilingual apps or analyzing text data, language identification provides valuable insights into understanding and processing textual content.

https://github.com/BKMbigo/MLKit_Sample/assets/102836149/37c8777a-f782-4f69-8937-6ca8ee827a21

#### [Translation](https://developers.google.com/ml-kit/language/translation)
The Translation feature allows seamless translation of text from one human language to another. With support for 58 popular human languages, ML Kit empowers Android developers to create applications that break down language barriers and foster cross-cultural communication. Whether you're building a language learning app or enabling global collaboration, the translation feature is an essential tool in your Android development toolkit.

https://github.com/BKMbigo/MLKit_Sample/assets/102836149/d075011f-38f0-4ec4-931e-0007497858ed

#### [Smart Reply](https://developers.google.com/ml-kit/language/smart-reply)
The Smart Reply feature enhances user experience by automatically generating short and contextually relevant replies in a conversation. Powered by machine learning algorithms, this feature suggests appropriate responses based on the input message (Uses up to 10 recent messages to generate appropriate responses). Currently, the Smart Reply feature is available for English conversations, providing users with quick and convenient reply suggestions in their daily communication.

https://github.com/BKMbigo/MLKit_Sample/assets/102836149/f065ff19-fc4c-4663-9ab0-99b477506c7a

#### [Entity Extraction](https://developers.google.com/ml-kit/language/entity-extraction)
The Entity Extraction feature allows you to extract specific information from a block of text. ML Kit's powerful entity recognition models can identify and extract various types of entities, such as addresses, phone numbers, dates and times, flight numbers, ISBN and IBAN book numbers, monetary amounts, tracking numbers, and payment card details. This feature is particularly useful in Android applications that require parsing and understanding structured data from unstructured text sources.

https://github.com/BKMbigo/MLKit_Sample/assets/102836149/1b6fe79c-520f-4e6c-ba39-2ee2654803bc

## Libraries Used
* Jetpack Compose
* [Google ML Kit](https://developers.google.com/ml-kit)

## Future Plans
* Provide Implementations of actions present in the UI
  * Copy Content
  * Share Content
  * Dail Number
  * Message Number
  * Add Number to contacts
  * Open Calendar
* Add persistence (Use a database to store previous results)
* Add vision features
  * [Image Labeling](https://developers.google.com/ml-kit/vision/image-labeling)
  * [Object detection and tracking](https://developers.google.com/ml-kit/vision/object-detection)
  * [Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning)
  * [Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/v2)
  * [Face Detection](https://developers.google.com/ml-kit/vision/face-detection)
  * [Face mesh detection (Beta)](https://developers.google.com/ml-kit/vision/face-mesh-detection)
  * [Pose Detection (Beta)](https://developers.google.com/ml-kit/vision/pose-detection)
  * [Selfie Segmentation (Beta)](https://developers.google.com/ml-kit/vision/selfie-segmentation)
  * [Digital Ink Recognition](https://developers.google.com/ml-kit/vision/digital-ink-recognition)
