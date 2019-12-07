#include <assert.h>
#include <stdio.h>
#include <libstring.h>
#include <unistd.h>
#include "tp.sch"

int std::main (const int argc, const char **argv)
{
	if (argc < 2) {
		std::printf("Usage: tp file1.mkv [file2.mkv ...]\n");
		return 1;
	}

	struct tp::s_session session = tp::setup_session(argc, argv);
	tp::play(&session);

	tp::destroy_session(&session);

	return 0;
}

static struct tp::s_session tp::setup_session(const int argc, const char **argv)
{
	std::assert(argc > 1);

	struct tp::s_session session = {
		.queue_length = argc -1,
		.playback_queue = malloc((argc -1) * sizeof(string::string))
	};

	for (int i=1; i<argc; ++i) {
		if (access(argv[i],  F_OK) == -1) {
			std::printf("Error: Could not read %s. Does the file exist?\n", argv[i]);
			exit(1);
		}

		session.playback_queue[i -1] = string::create((char *)argv[i]);
	}

	return session;
}

static void tp::destroy_session(struct tp::s_session *session)
{
	assert(session->queue_length > 0);
	for (int i=0; i<session->queue_length; ++i)
		string::free(&session->playback_queue[i]);

	std::free(session->playback_queue);
}

static void tp::play(struct tp::s_session *session)
{
	autofree string::string command = string::create(MPLAYER_CMD);
	for (int i=0; i<session->queue_length; ++i) {
		string::append(&command, "'");
		string::append(&command, session->playback_queue[i].bytes);
		string::append(&command, "' ");
	}

	std::system(command.bytes);
}
