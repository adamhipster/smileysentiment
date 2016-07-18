# -*- coding: utf-8 -*-

import sys 
import tweepy 
import webbrowser

# Query terms

Q = sys.argv[1:3]
V = sys.argv[2:3]

CONSUMER_KEY = 'conskey' #'YOUR-CONSUMER-KEY'
CONSUMER_SECRET = 'conssecret' #'YOUR-CONSUMER-SECRET'
ACCESS_TOKEN = 'accesstoken' #'YOUR-ACCESS-TOKEN'
ACCESS_TOKEN_SECRET = 'accesstokensecret' #'YOUR-ACCESS-SECRET'

auth = tweepy.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET) 
auth.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)

reload(sys)
sys.setdefaultencoding( 'utf-8' )

class CustomStreamListener(tweepy.StreamListener): 

    def on_status(self, status):
        
        text = status.text
        author = status.author.screen_name
        created_at = status.created_at
        source = status.source
        
      
        try: print "%s\t%s\t%s\t%s" % (text,
                                       author, 
                                       created_at, 
                                       source,)
        except Exception, e: 
            print >> sys.stderr, 'Encountered Exception:', e 
            pass

    def on_error(self, status_code): 
        print >> sys.stderr, 'Encountered error with status code:', status_code 
        return True # Don't kill the stream

    def on_timeout(self): 
        print >> sys.stderr, 'Timeout...'
        return True # Don't kill the stream

streaming_api = tweepy.streaming.Stream(auth, CustomStreamListener(), timeout=120)
print >> sys.stderr, 'Filtering the public timeline for "%s" ' % (' '.join(sys.argv[1:3]),)
#streaming_api.filter(follow=None, track=("@"))
streaming_api.buffer_size = 10000
streaming_api.sample(async=False)






