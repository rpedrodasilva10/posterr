INSERT INTO public.users (created_at, post_count, username)
values (now(), 0, 'you'),
       (now(), 0, 'should'),
       (now(), 0, 'hire'),
       (now(), 0, 'me') on conflict do nothing ;