# Covid Updates API


## Getting Started ##
- Clone the project in your system
- Make sure you have docker installed to start the application
- After cloning, open a terminal, go to the root folder of the project and perform the
  following commands.

    1. docker image build -t covidupdates_image .
    2. docker run -p 8080:8080 covidupdates_image

  The first command will build a docker image that consists of jar file of the project. The
  second command will run the image and it can be accessed locally in port 8080. localhost:8080