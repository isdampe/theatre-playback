#ifndef TP_H
#define TP_H
#include <stdio.h>
#include <libstring.h>

#define MPLAYER_CMD "mplayer -quiet -fs -fixed-vo "

namespace tp;

struct s_session {
	string::string *playback_queue;
	int queue_length;
};

static struct s_session setup_session(const int argc, const char **argv);
static void destroy_session(struct s_session *session);
static void play(struct s_session *session);

#endif